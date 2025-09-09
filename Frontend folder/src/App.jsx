
import { BrowserRouter } from 'react-router';
import { Routing } from './components/common/Routing';

import 'C:/reactjs/projectstructure/src/Background.css'
import { useEffect } from 'react';

function App() {

  useEffect(() => {
    document.title = "SwiftBus"; 
  }, []);
 return (
    <div
      
    >
      <BrowserRouter>
        <Routing />
      </BrowserRouter>
    </div>
  );
}

export default App
