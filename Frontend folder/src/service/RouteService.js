
import axiosInstance from "../http-common";

const RouteService = {
    getRoutes : async() => {
    return await axiosInstance.get("/route/getall"); 
  },
  deleteRoute : async(id) => {
    return await axiosInstance.delete(`/route/delete/${id}`);
  },
    addRoute : async (route) =>{
    return await  axiosInstance.post("/route/add", route);
  },
  getRouteByOp : async () =>{
    return await axiosInstance.get(`/route/getbyoperator`);
  },
  updateRoute: async (route) => {
    return await axiosInstance.put("/route/update", route);
  },

  getRouteById: async (routeId) =>{
    return await axiosInstance.get(`/route/getbyid/${routeId}`);
  }

  
}

export default RouteService;