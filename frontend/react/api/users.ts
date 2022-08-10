import axios from "axios";

import {SERVER_BASE_URL} from "../utils/constants";

const UserAPI = {
  current: async () => {
    const user: any = window.localStorage.getItem("user");
    const token = user?.token;
    try {
      return await axios.get(`/user`, {
        headers: {
          Authorization: `Bearer ${encodeURIComponent(token)}`,
        },
      });
    } catch (error: any) {
      return error.response;
    }
  },
  login: async (email: string, password: string) => {
    try {
      return await axios.post(
        `${SERVER_BASE_URL}/auth/login`,
        JSON.stringify({'email': email, 'password': password}),
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
    } catch (error: any) {
      return error.response;
    }
  },
  signup: async (email: string, password: string) => {
    try {
      return await axios.post(
        `${SERVER_BASE_URL}/auth/signup/`,
        JSON.stringify({email, password}),
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
    } catch (error: any) {
      return error.response;
    }
  },

  save: async (user: object) => {
    try {
      return await axios.put(
        `${SERVER_BASE_URL}/user`,
        JSON.stringify({user}),
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
    } catch (error: any) {
      return error.response;
    }
  },

};

export default UserAPI;