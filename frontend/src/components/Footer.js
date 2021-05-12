import React, {useEffect, useState} from "react";
const API_URL = "http://localhost:4444/api/version";

const Footer = () => {
  const [version, setVersion] = useState('DEBUG')
  const getVersion = () =>
  {
    
    fetch(API_URL)
    .then(response => response.text())
    .then(data => {
      console.log(data);
      setVersion(data)
    })
  }
  useEffect(() => {
    getVersion()
  }, [])

  return <footer className="footer">
    HotPoll version: {version}
  </footer>;
};

export default Footer;
