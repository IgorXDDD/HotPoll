import React, { useState, useEffect } from 'react';
import gsap from 'gsap';

const BarGraph = ({ aid, inputPercentage }) => {
  const [finalPercentage, setFinalPercentage] = useState(0);

  useEffect(() => {
    const initialValue = {
      percentage: 0,
    };
    const targetValue = {
      percentage: inputPercentage,
    };

    let tl = gsap.timeline({ defaults: { ease: 'Power4.easeOut' } });
    let barGraphIndicator = document.getElementById(aid);
    tl.to(barGraphIndicator, { duration: 3, width: inputPercentage + '%' }).to(
      initialValue,
      {
        percentage: targetValue.percentage,
        onUpdate: () => {
          setFinalPercentage(initialValue.percentage);
        },
        duration: 3,
      },
      '<'
    );
  }, []);

  return (
    <div className="bar-graph-wrapper">
      <div id={aid} className="bar-graph-indicator"></div>
      <p className="bar-graph-percentage">{Math.round(finalPercentage)}%</p>
    </div>
  );
};

export default BarGraph;
