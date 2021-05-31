import axios from "axios";
import { useGlobalContext } from "../context";

const API_URL = "http://localhost:4444/api/auth/";

class AuthService {

  
  login(username, password) {
    console.log("NO LOGUJEMY SIE");
    return axios
      .post(API_URL + "signin", {
        username,
        password
      })
      .then(response => {
        // if (response.data.accessToken) {
        //   localStorage.setItem("user", JSON.stringify(response.data));
        // }
        sessionStorage.setItem("user", JSON.stringify(response.data));
        return response.data;
      })
      .
      catch(()=>{
        console.log("nie udalo sie zalgowoac");
      })
  }

  logout() {
    sessionStorage.removeItem("user");
  }

  register(username, email, password) {
    return axios.post(API_URL + "signup", {
      username,
      email,
      password
    });
  }

  getCurrentUser() {
    return JSON.parse(sessionStorage.getItem('user'));;
  }
}

export default new AuthService();
