
import { useState,useEffect } from "react";

import RouteService from "../../service/RouteService";

export default function AdminRoutes() {
  const [routes, setRoutes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchId, setSearchId] = useState("");

  
  const fetchRoutes = async () => {
    try {
      setLoading(true);
      const response = await RouteService.getRoutes();
      setRoutes(response.data);
      setLoading(false);
    } catch (err) {
      setError("Failed to fetch routes");
      setLoading(false);
    }
  };

  
  const deleteRoute = async (id) => {
    if (window.confirm(`Are you sure you want to delete route ${id}?`)) {
      try {
        await RouteService.deleteRoute(id);
        setRoutes(routes.filter((route) => route.routeId !== id));
      } catch (err) {
        alert("Failed to delete route");
      }
    }
  };

  
  const handleSearch = async () => {
    if (!searchId) {
      fetchRoutes();
      return;
    }

    try {
      const response = await RouteService.getRouteById(searchId);
      if (response.data) {
        setRoutes([response.data]); 
        setError("");
      } else {
        setRoutes([]);
        setError("No route found with this ID");
      }
    } catch (err) {
      setError("Failed to fetch route by ID");
    }
  };

  useEffect(() => {
    fetchRoutes();
  }, []);

  if (loading) return <p className="text-center mt-4">Loading routes...</p>;
  if (error) return <p className="text-danger text-center mt-4">{error}</p>;

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4 fw-bold text-primary">Manage Routes</h2>

      
      <div className="d-flex justify-content-center mb-4">
        <input
          type="text"
          className="form-control w-25 me-2"
          placeholder="Enter Route ID"
          value={searchId}
          onChange={(e) => setSearchId(e.target.value)}
        />
        <button className="btn btn-primary me-2" onClick={handleSearch}>
          Search
        </button>
        <button className="btn btn-secondary" onClick={fetchRoutes}>
          Reset
        </button>
      </div>

     
      <table className="table table-bordered table-striped shadow-sm">
        <thead className="table-primary text-center">
          <tr>
            <th>Route ID</th>
            <th>Source</th>
            <th>Destination</th>
            <th>Distance (km)</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody className="text-center align-middle">
          {routes.map((route) => (
            <tr key={route.routeId}>
              <td>{route.routeId}</td>
              <td>{route.origin}</td>
              <td>{route.destination}</td>
              <td>{route.distanceKm}</td>
              <td>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => deleteRoute(route.routeId)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
          {routes.length === 0 && (
            <tr>
              <td colSpan="5" className="text-center text-muted">
                No routes available
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}