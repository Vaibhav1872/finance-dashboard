import { useEffect, useState } from "react";
import API from "../services/api";
import "./dashboard.css";

function Dashboard({ setLoggedIn }) {

  const [records, setRecords] = useState([]);
  const [summary, setSummary] = useState({
    income: 0,
    expense: 0,
    balance: 0
  });

  // ✅ FILTER STATES
  const [filterType, setFilterType] = useState("");
  const [filterCategory, setFilterCategory] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("");
  const [type, setType] = useState("EXPENSE");
  const [notes, setNotes] = useState("");

  const [editId, setEditId] = useState(null);

  const [newUsername, setNewUsername] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newRole, setNewRole] = useState("ROLE_VIEWER");

  const role = localStorage.getItem("role");

  // ✅ FETCH DATA
  const fetchData = () => {
    API.get("/records")
      .then(res => setRecords(res.data))
      .catch(err => alert(err.response?.data || "Error fetching records"));
  };

  const fetchSummary = () => {
    API.get("/dashboard/summary")
      .then(res => setSummary(res.data))
      .catch(err => alert(err.response?.data || "Error fetching summary"));
  };

  // ✅ FILTER FUNCTIONS (FIXED POSITION)
  const filterByType = () => {
    API.get(`/records/filter/type?type=${filterType}`)
      .then(res => setRecords(res.data))
      .catch(() => alert("Filter failed"));
  };

  const filterByCategory = () => {
    API.get(`/records/filter/category?category=${filterCategory}`)
      .then(res => setRecords(res.data))
      .catch(() => alert("Filter failed"));
  };

  const filterByDate = () => {
    API.get(`/records/filter/date?start=${startDate}&end=${endDate}`)
      .then(res => setRecords(res.data))
      .catch(() => alert("Filter failed"));
  };

  useEffect(() => {
    fetchData();
    if (role === "ROLE_ANALYST" || role === "ROLE_ADMIN") {
      fetchSummary();
    }
  }, []);

  // ✅ ADD / UPDATE
  const saveRecord = () => {
    if (!amount || !category) {
      alert("Amount and Category required");
      return;
    }

    const data = {
      amount: Number(amount),
      category,
      recordType: type,
      recordDate: new Date().toISOString().split("T")[0],
      notes
    };

    const request = editId
      ? API.put(`/records/${editId}`, data)
      : API.post("/records", data);

    request
      .then(() => {
        setEditId(null);
        fetchData();
        if (role !== "ROLE_VIEWER") fetchSummary();
      })
      .catch(err => alert(err.response?.data || "Operation failed"));

    setAmount("");
    setCategory("");
    setNotes("");
  };

  const deleteRecord = (id) => {
    API.delete(`/records/${id}`)
      .then(() => {
        fetchData();
        if (role !== "ROLE_VIEWER") fetchSummary();
      })
      .catch(err => alert(err.response?.data || "Delete failed"));
  };

  const editRecord = (r) => {
    setEditId(r.id);
    setAmount(r.amount);
    setCategory(r.category);
    setType(r.recordType);
    setNotes(r.notes);
  };

  const createUser = () => {
    API.post("/admin/users", {
      username: newUsername,
      passwordHash: newPassword,
      role: { name: newRole },
      status: "ACTIVE"
    })
      .then(() => {
        alert("User Created");
        setNewUsername("");
        setNewPassword("");
      })
      .catch(err => alert(err.response?.data || "User creation failed"));
  };

  const logout = () => {
    localStorage.clear();
    setLoggedIn(false);
  };

  return (
    <div className="dashboard">

      <div className="navbar">
        <h2>Finance Dashboard</h2>
        <span>{role}</span>
        <button className="logout" onClick={logout}>Logout</button>
      </div>

      {(role === "ROLE_ANALYST" || role === "ROLE_ADMIN") && (
        <div className="cards">
          <div className="card income">
            <h3>Income</h3>
            <p>₹{summary.income}</p>
          </div>
          <div className="card expense">
            <h3>Expense</h3>
            <p>₹{summary.expense}</p>
          </div>
          <div className="card balance">
            <h3>Balance</h3>
            <p>₹{summary.balance}</p>
          </div>
        </div>
      )}

      {/* FILTER UI */}
      <div className="form">
        <h3>Filter Records</h3>

        <select onChange={(e) => setFilterType(e.target.value)}>
          <option value="">Select Type</option>
          <option value="INCOME">Income</option>
          <option value="EXPENSE">Expense</option>
        </select>
        <button onClick={filterByType}>Filter</button>

        <input
          placeholder="Category"
          value={filterCategory}
          onChange={(e) => setFilterCategory(e.target.value)}
        />
        <button onClick={filterByCategory}>Filter</button>

        <input type="date" onChange={(e) => setStartDate(e.target.value)} />
        <input type="date" onChange={(e) => setEndDate(e.target.value)} />
        <button onClick={filterByDate}>Filter</button>

        <button onClick={fetchData}>Reset</button>
      </div>

      {/* ADMIN */}
      {role === "ROLE_ADMIN" && (
        <>
          <div className="form">
            <h3>{editId ? "Edit Record" : "Add Record"}</h3>

            <input value={amount} placeholder="Amount"
              onChange={(e) => setAmount(e.target.value)} />

            <input value={category} placeholder="Category"
              onChange={(e) => setCategory(e.target.value)} />

            <select value={type} onChange={(e) => setType(e.target.value)}>
              <option value="EXPENSE">Expense</option>
              <option value="INCOME">Income</option>
            </select>

            <input value={notes} placeholder="Notes"
              onChange={(e) => setNotes(e.target.value)} />

            <button onClick={saveRecord}>
              {editId ? "Update" : "Add"}
            </button>
          </div>

          <div className="form">
            <h3>Create User</h3>

            <input placeholder="Username"
              value={newUsername}
              onChange={(e) => setNewUsername(e.target.value)} />

            <input placeholder="Password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)} />

            <select value={newRole}
              onChange={(e) => setNewRole(e.target.value)}>
              <option value="ROLE_VIEWER">Viewer</option>
              <option value="ROLE_ANALYST">Analyst</option>
              <option value="ROLE_ADMIN">Admin</option>
            </select>

            <button onClick={createUser}>Create</button>
          </div>
        </>
      )}

      <table className="table">
        <thead>
          <tr>
            <th>Category</th>
            <th>Amount</th>
            <th>Type</th>
            <th>Date</th>
            <th>Notes</th>
            {role === "ROLE_ADMIN" && <th>Action</th>}
          </tr>
        </thead>

        <tbody>
          {records.map(r => (
            <tr key={r.id}>
              <td>{r.category}</td>
              <td>₹{r.amount}</td>
              <td>{r.recordType}</td>
              <td>{r.recordDate}</td>
              <td>{r.notes}</td>

              {role === "ROLE_ADMIN" && (
                <td>
                  <button onClick={() => editRecord(r)}>Edit</button>
                  <button onClick={() => deleteRecord(r.id)}>Delete</button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>

    </div>
  );
}

export default Dashboard;