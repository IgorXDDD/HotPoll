import React from "react";
import Poll from "./Poll";
import Loading from "./Loading";
import { useGlobalContext } from "../context";

const PollList = () => {
  const { polls, loading } = useGlobalContext();
  console.log("polls in PollList.js ", polls);

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
