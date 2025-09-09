import { useState,useEffect } from "react";
import AdminService from "../../service/AdminService";

export default function BusOperatorApproval() {
  const [pendingOperators, setPendingOperators] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchPendingOperators = async () => {
    try {
      const res = await AdminService.pendingRequest();
      let operators = [];
      if (Array.isArray(res.data)) {
        operators = res.data;
      } else if (res.data.pendingOperators) {
        operators = res.data.pendingOperators;
      }
      operators = operators.filter(op => op.status === "PENDING");
      setPendingOperators(operators);
      setLoading(false);
    } catch (err) {
      setError("Failed to fetch pending bus operators");
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPendingOperators();
  }, []);

  const handleStatusChange = async (busOpId, action) => {
    try {
      await AdminService.updateBusOperatorStatus(busOpId, action);
      alert(`Bus Operator ${action}d successfully`);
      fetchPendingOperators();
    } catch (err) {
      alert(`Failed to ${action} bus operator`);
    }
  };

  if (loading) return <p className="text-center mt-4">Loading pending bus operators...</p>;

  return (
    <div className="container my-5">
      <h3 className="text-center mb-4 fw-bold text-primary">Pending Bus Operator Requests</h3>
      {error && <p className="text-danger text-center">{error}</p>}

      <div className="table-responsive shadow-sm">
        <table className="table table-hover table-bordered align-middle text-center">
          <thead className="table-dark">
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {pendingOperators.length === 0 ? (
              <tr>
                <td colSpan="3" className="text-center text-muted">No pending requests</td>
              </tr>
            ) : (
              pendingOperators.map((op) => (
                <tr key={op.busOpId} className="align-middle">
                  <td>{op.busOpId}</td>
                  <td>{op.username}</td>
                  <td>
                    <button
                      className="btn btn-success btn-sm me-2"
                      onClick={() => handleStatusChange(op.busOpId, "approve")}
                    >
                      Approve
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleStatusChange(op.busOpId, "reject")}
                    >
                      Reject
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}