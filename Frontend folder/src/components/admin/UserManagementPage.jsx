import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router";

import UserService from "../../service/UserService";


export default function UserManagementPage() {
  const [users, setUsers] = useState([]);
  const [userId, setUserId] = useState("");
  const [singleUser, setSingleUser] = useState(null);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchAllUsers();
  }, []);

  const fetchAllUsers = async () => {
    try {
      const response = await UserService.getAllUsers();
      let data = Array.isArray(response.data) ? response.data : [response.data];

      const cleaned = data.map((u) => ({
        userdataId: u.userdataId,
        userId: u.userId,
        name: u.name,
        email: u.email,
        contactNumber: u.contactNumber,
        address: u.address,
        dateOfBirth: u.dateOfBirth,
      }));

      setUsers(cleaned);
      setError("");
    } catch (err) {
      console.error(err);
      setError("Failed to fetch users.");
      setUsers([]);
    }
  };

  const fetchUserById = async () => {
    if (!userId) return;
    try {
      const response = await UserService.getUserById(userId);
      const u = response.data;
      const cleaned = {
        userdataId: u.userdataId,
        userId: u.userId,
        name: u.name,
        email: u.email,
        contactNumber: u.contactNumber,
        address: u.address,
        dateOfBirth: u.dateOfBirth,
      };
      setSingleUser(cleaned);
      setError("");
    } catch (err) {
      console.error(err);
      setError("User not found!");
      setSingleUser(null);
    }
  };

  const deleteUser = async (id) => {
    if (!window.confirm("Are you sure you want to delete this user?")) return;

    try {
      const response = await UserService.deleteUser(id);
      setMessage(response.data);
      setError("");
      fetchAllUsers();
      if (singleUser?.userId === id) setSingleUser(null);
    } catch (err) {
      console.error(err);
      setError("Failed to delete user.");
      setMessage("");
    }
  };

  const filteredUsers = singleUser ? [singleUser] : users;

  return (
    <div className="container my-5">
      <h2 className="text-center mb-4 fw-bold text-primary">User Management</h2>

      {error && <div className="alert alert-danger">{error}</div>}
      {message && <div className="alert alert-success">{message}</div>}

      <div className="d-flex justify-content-center mb-4">
        <input
          type="number"
          placeholder="Enter User ID"
          className="form-control w-25 me-2"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
        />
        <button className="btn btn-primary" onClick={fetchUserById}>
          Get User
        </button>
        <button
          className="btn btn-secondary ms-2"
          onClick={() => {
            setSingleUser(null);
            setUserId("");
            fetchAllUsers();
          }}
        >
          Reset
        </button>
      </div>

      <div className="table-responsive shadow-sm rounded">
        <table className="table table-bordered table-hover align-middle text-center">
          <thead className="table-dark">
            <tr>
              <th>User ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Address</th>
              <th>DOB</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredUsers.length > 0 ? (
              filteredUsers.map((u) => (
                <tr key={u.userId}>
                  <td>{u.userId}</td>
                  <td>{u.name}</td>
                  <td>{u.email}</td>
                  <td>{u.contactNumber}</td>
                  <td>{u.address}</td>
                  <td>{u.dateOfBirth}</td>
                  <td>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => deleteUser(u.userId)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="7" className="text-center text-muted">
                  No users found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <div className="text-center mt-4">
        <button
          className="btn btn-secondary"
          onClick={() => navigate("/admin/dashboard")}
        >
          Back to Dashboard
        </button>
      </div>
    </div>
  );
}