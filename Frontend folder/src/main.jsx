import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
//import './index.css'
import App from './App.jsx'

import { Routing } from './components/common/Routing.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />

   
  </StrictMode>,
)
