import React, { useEffect, useState } from 'react'
const API_URL = 'http://localhost:4444/api/version'

const Footer = () => {
  const [version, setVersion] = useState('DEBUG')
  const getVersion = () => {
    fetch(API_URL)
      .then((response) => response.text())
      .then((data) => {
        setVersion(data)
      })
  }
  useEffect(() => {
    getVersion()
  }, [])

  return (
    <footer className='footer'>
      <p>HotPoll version: {version}</p>
    </footer>
  )
}

export default Footer
