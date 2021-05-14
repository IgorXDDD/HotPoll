import React from "react";
import Poll from "./Poll";
import Loading from "./Loading";
// import AuthService from "../services/user.service";
import { useGlobalContext } from "../context";
// const API_URL = "http://localhost:4444/api/poll?pollId=2137";

const PollList = () => {
  const { polls, loading } = useGlobalContext();
  console.log("polls in PollList.js ", polls);

  // if (AuthService.getCurrentUser()) {
  //   fetch(API_URL, {
  //     method: "GET",
  //     headers: {
  //       Authorization: "Bearer " + AuthService.getCurrentUser.jwt,
  //     },
  //   });
  // }

  if (loading) {
    return <Loading />;
  } else if (polls.length < 1) {
    return <h2>No polls</h2>;
  }
  return (
    <>
      {polls.map((item) => {
        return <Poll key={item.id} {...item} />;
      })}
    </>
  );
};

export default PollList;
