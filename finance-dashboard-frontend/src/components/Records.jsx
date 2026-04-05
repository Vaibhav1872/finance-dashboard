import { useState } from "react";
import API from "../services/api";

function Records() {
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("");

  const addRecord = () => {
    API.post("/records", {
      amount,
      category,
      recordType: "EXPENSE",
      recordDate: "2026-04-03",
    })
      .then(() => alert("Added"))
      .catch((err) => console.log(err));
  };

  return (
    <div>
      <h2>Add Record</h2>

      <input
        placeholder="Amount"
        onChange={(e) => setAmount(e.target.value)}
      />

      <input
        placeholder="Category"
        onChange={(e) => setCategory(e.target.value)}
      />

      <button onClick={addRecord}>Add</button>
    </div>
  );
}

export default Records;