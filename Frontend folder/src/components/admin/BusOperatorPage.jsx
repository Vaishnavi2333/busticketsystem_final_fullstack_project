import { useEffect, useState } from "react";

import { useNavigate } from "react-router";
import BusOperatorService from "../../service/BusOperatorService";

export default function BusOperatorPage() {
  const [operators, setOperators] = useState([]);
  const [displayOperators, setDisplayOperators] = useState([]);
  const [operatorId, setOperatorId] = useState("");
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchAllOperators();
  }, []);

  const fetchAllOperators = async () => {
    try {
      const response = await BusOperatorService.getAllBusOp();
      const filtered = response.data.filter(
        (op) => op.name && op.licenceNumber
      );
      setOperators(filtered);
      setDisplayOperators(filtered);
    } catch (err) {
      setError("Failed to fetch operators.");
    }
  };

  const fetchOperatorById = async () => {
    if (!operatorId) {
      setDisplayOperators(operators);
      setError("");
      return;
    }
    try {
      const response = await BusOperatorService.getBusOpById(operatorId);
      const op = response.data;
      if (!op.name || !op.licenceNumber) {
        setDisplayOperators([]);
        setError("Operator profile is incomplete or rejected.");
      } else {
        setDisplayOperators([op]);
        setError("");
      }
    } catch (err) {
      setDisplayOperators([]);
      setError("Operator not found!");
    }
  };

  const deleteOperator = async (id) => {
    if (!window.confirm("Are you sure you want to delete this operator?")) return;
    try {
      const response = await BusOperatorService.deleteBusOp(id);
      setMessage(response.data);
      setError("");
      fetchAllOperators();
    } catch (err) {
      setError("Failed to delete operator.");
      setMessage("");
    }
  };

  return (
    <div className="container my-5">
      <h2 className="text-center mb-4 fw-bold text-primary">Bus Operator Management</h2>

      {error && <div className="alert alert-danger">{error}</div>}
      {message && <div className="alert alert-success">{message}</div>}

      <div className="d-flex justify-content-center mb-4">
  <input
    type="number"
    placeholder="Enter Operator ID"
    className="form-control w-25 me-2"
    value={operatorId}
    onChange={(e) => setOperatorId(e.target.value)}
  />
  <button className="btn btn-primary me-2" onClick={fetchOperatorById}>
    Search
  </button>
  <button
    className="btn btn-secondary"
    onClick={() => {
      setOperatorId("");
      setDisplayOperators(operators);
      setError("");
    }}
  >
    Reset
  </button>
</div>

      <div className="table-responsive shadow-sm">
        <table className="table table-bordered table-hover text-center">
          <thead className="table-dark">
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Company</th>
              <th>Address</th>
              <th>Contact</th>
              <th>Email</th>
              <th>DOB</th>
              <th>Gender</th>
              <th>Licence No</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {displayOperators.length === 0 ? (
              <tr>
                <td colSpan="10" className="text-center text-muted">
                  No bus operators found
                </td>
              </tr>
            ) : (
              displayOperators.map((op) => (
                <tr key={op.busOpLoginId}>
                  <td>{op.busOpLoginId}</td>
                  <td>{op.name}</td>
                  <td>{op.companyName}</td>
                  <td>{op.address}</td>
                  <td>{op.contactNumber}</td>
                  <td>{op.email}</td>
                  <td>{op.dateOfBirth}</td>
                  <td>{op.gender}</td>
                  <td>{op.licenceNumber}</td>
                  <td>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => deleteOperator(op.busOpLoginId)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      <button
        className="btn btn-secondary mt-4"
        onClick={() => navigate("/admin/dashboard")}
      >
        Back to Dashboard
      </button>
    </div>
  );
}