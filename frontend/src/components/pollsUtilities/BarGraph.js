import React, { useState, useEffect } from 'react'
import gsap from "https://cdn.skypack.dev/gsap";

const BarGraph = (props) => {
  const [finalPercentage, setFinalPercentage] = useState(0);
  
  useEffect(() => {
    const initialValue = {
      percentage: 0
    };
    const targetValue = {
      percentage: props.percentage
    };

    let tl = gsap.timeline({ defaults: { ease: "Power4.easeOut" } });
    let barGraphIndicator = document.getElementById(props.aid);
    tl.to(barGraphIndicator, 3, {
      width: props.percentage + "%"
    }).to(
      initialValue,
      {
        percentage: targetValue.percentage,
        onUpdate: () => {
          setFinalPercentage(initialValue.percentage);
        },
        duration: 3
      },
      "<"
    );
  }, []);

  return (
    <div className="bar-graph-wrapper">
      <div id={props.aid} className="bar-graph-indicator"></div>
      <p className="bar-graph-percentage">{Math.round(finalPercentage)}%</p>
    </div>
  );
};

export default BarGraph
