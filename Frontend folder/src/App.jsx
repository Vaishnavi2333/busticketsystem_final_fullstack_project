import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import { BrowserRouter } from 'react-router';
import { Routing } from './components/common/Routing';
//import backgroundImage from './assets/image2303.jpg';
// import './App.css'
// // import { LoginPage } from './LoginPage'
// // import { BrowserRouter,Routes,Route } from 'react-router-dom'
// // import { RegistrationPage } from './RegistrationPage'
// // import { UserDashBoard } from './UserDashboard'
// // import { SelectSeat } from './SelectSeat'
import 'C:/reactjs/projectstructure/src/Background.css'
import { useEffect } from 'react';

function App() {

  useEffect(() => {
    document.title = "SwiftBus"; 
  }, []);
 return (
    <div
      // style={{
      //   minHeight: "100vh",
      //   backgroundImage: `url(${backgroundImage})`,
      //   backgroundSize: "cover",
      //   backgroundPosition: "center",
      //   backgroundRepeat: "no-repeat"
      // }}
    >
      <BrowserRouter>
        <Routing />
      </BrowserRouter>
    </div>
  );
}

export default App
