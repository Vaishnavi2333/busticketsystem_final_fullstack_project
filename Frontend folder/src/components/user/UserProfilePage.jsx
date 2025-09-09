import { useState } from "react";
import UserService from "../../service/UserService";
import { useEffect } from "react";
import { useNavigate } from "react-router";
import { useLocation } from "react-router";

export function UserProfilePage() {
  const [formData, setFormData] = useState({
    name: "",
    gender: "",
    dateOfBirth: "",
    email: "",
    contactNumber: "",
    address: "",
  });

  const [message, setMessage] = useState("");
  const [userId, setUserId] = useState(null);
  const [userdataId, setUserdataId] = useState(null);
  const navigate = useNavigate();
  const location = useLocation(); 
  const fromBooking = location.state?.fromBooking || false;

  useEffect(() => {
    const fetchUserProfile = async () => {
      const id = localStorage.getItem("userId");
      if (!id) return;

      try {
        const response = await UserService.getUserById(id);
        if (response.data) {
          setFormData({
            name: response.data.name || "",
            gender: response.data.gender || "",
            dateOfBirth: response.data.dateOfBirth || "",
            email: response.data.email || "",
            contactNumber: response.data.contactNumber || "",
            address: response.data.address || "",
          });
          setUserId(response.data.userId);
          setUserdataId(response.data.userdataId);
        }
      } catch (err) {
        console.error("Failed to fetch user profile:", err);
      }
    };

    fetchUserProfile();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const validateForm = () => {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(formData.email)) {
      setMessage("Please enter a valid email address!");
      return false;
    }

    if (new Date(formData.dateOfBirth) > new Date()) {
      setMessage("Date of birth cannot be in the future!");
      return false;
    }

    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    if (!validateForm()) return;

    try {
      let response;

      if (!userdataId) {
        response = await UserService.createUser(formData);
      } else {
        const payload = { ...formData, userId, userdataId };
        response = await UserService.updateUser(payload);
      }

      setMessage("Profile saved successfully!");
      console.log(response.data);

      if (response.data.userdataId) {
        setUserdataId(response.data.userdataId);
      }

      localStorage.setItem("profileComplete", "true");

      const pendingBooking = sessionStorage.getItem("pendingBooking");
      if (pendingBooking) {
        const bookingData = JSON.parse(pendingBooking);
        sessionStorage.removeItem("pendingBooking");
        navigate("/booking", {
          state: {
            ...bookingData,
            userId: parseInt(localStorage.getItem("userId")),
          },
        });
      } else {
        navigate("/user/dashboard");
      }
    } catch (err) {
      console.error(err);
      setMessage("Error saving profile.");
    }
  };

  return (
    <div className="container mt-5">
      <h2>User Profile</h2>
      {message && <div className="alert alert-info">{message}</div>}

      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>Name</label>
          <input
            type="text"
            className="form-control"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label>Gender</label>
          <select
            className="form-select"
            name="gender"
            value={formData.gender}
            onChange={handleChange}
            required
          >
            <option value="">Select</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Other">Other</option>
          </select>
        </div>

        <div className="mb-3">
          <label>Date of Birth</label>
          <input
            type="date"
            className="form-control"
            name="dateOfBirth"
            value={formData.dateOfBirth}
            onChange={handleChange}
            max={new Date().toISOString().split("T")[0]}
            required
          />
        </div>

        <div className="mb-3">
          <label>Email</label>
          <input
            type="email"
            className="form-control"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label>Contact Number</label>
          <input
            type="text"
            className="form-control"
            name="contactNumber"
            value={formData.contactNumber}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label>Address</label>
          <textarea
            className="form-control"
            name="address"
            value={formData.address}
            onChange={handleChange}
            required
          ></textarea>
        </div>

        <button type="submit" className="btn btn-success">
          Save Profile
        </button>
      </form>
    </div>
  );
}
