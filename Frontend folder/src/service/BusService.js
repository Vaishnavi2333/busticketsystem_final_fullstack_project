
import axiosInstance from "../http-common";

const BusService = {

addBus: async (busData) => {
    return await axiosInstance.post("/bus/add", busData);
  },

  getAllBus: async () => {
    return await axiosInstance.get("/bus/getallbus");

  },

   deleteBus : async(id) => {
    return await axiosInstance.delete(`/bus/delete/${id}`);
  },

  getBusByOpId : async(operatorId) => {
    return await axiosInstance.get(`/bus/getbusbyoperator/${operatorId}`);
  },

  searchBuses: (origin, destination, date) => {
    return axiosInstance.get(`/bus/search/${origin}/${destination}/${date}`);
  },

  getAvailableSeats: (tripId) => {
    return axiosInstance.get(`/bus/available-seats/${tripId}`);
  },

  updateBus: async (bus) => {
    return await axiosInstance.put("/bus/update", bus);
  },
 addBusAmenity: async (amenity) => {
    return await axiosInstance.post("/busamenity/add", amenity);
  },

  removeBusAmenity: async (busAmenityId) => {
    return await axiosInstance.delete(`/busamenity/remove/${busAmenityId}`);
  },
  getAmenitiesByBusId: async (busId) =>{
    return await axiosInstance.get(`/busamenity/getbybusid/${busId}`);
  },

  getBusByBusId : async(busId) =>{
    return await axiosInstance.get(`/bus/getbusbyid/${busId}`)
  }
};

export default BusService;