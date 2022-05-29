// React
import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
// Bootstrap
import 'bootstrap/dist/css/bootstrap.min.css';
// Pages
import Homepage from './pages/Homepage';
import Staff from './pages/Staff';
import Tasks from './pages/Tasks';
import Riders from './pages/Riders';
import Stores from './pages/Stores';
import RiderProfile from './pages/RiderProfile';
import Login from './pages/Login';
import NewApplications from './pages/NewApplications';
import StoreTasks from './pages/StoreTasks';

export let staff = [
  { id: 1, name: "Afonso Campos", rating: 5, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 10, workZone: 'A' },
  { id: 2, name: "Diana Siso", rating: 4, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 1, workZone: 'B' },
  { id: 3, name: "Isabel Rosário", rating: 2, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 2, workZone: 'D' },
  { id: 4, name: "Miguel Ferreira", rating: 3, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 2, workZone: 'C' },
  { id: 5, name: "Dinis Lei", rating: 5, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 9, workZone: 'B' },
  { id: 6, name: "Diogo Monteiro", rating: 4, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 8, workZone: 'D' },
  { id: 7, name: "Camila Fonseca", rating: 4, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 4, workZone: 'B' },
  { id: 8, name: "Lucius Vinicius", rating: 5, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 4, workZone: 'A' },
  { id: 9, name: "Tomé Carvalho", rating: 1, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 5, workZone: 'C' },
  { id: 10, name: "Rodrigo Lima", rating: 3, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 2, workZone: 'A' },
]

// time and completion in seconds
// distance: km
// time: seconds (time since task was submitted to the system by client)
// completion: seconds (estimated time for task completion)
export let tasks = [
  { id: 1, riderId: 8, storeId: 6, delivery: 'Made-up street, No. 18, Aveiro', distance: 10, time: 1860, completion: 1800},
  { id: 2, riderId: 5, storeId: 3, delivery: 'House avenue, No. 2, Porto', distance: 73, time: 2084, completion: 1560},
  { id: 3, riderId: 2, storeId: 2, delivery: 'Flowers street, No. 36, Aveiro', distance: 91, time: 1004, completion: 2700},
  { id: 4, riderId: 9, storeId: 5, delivery: 'Awesome corner, No. 1, Coimbra', distance: 22, time: 706, completion: 780},
]

export let stores = [
  { id: 1, name: 'WonderWine', address: 'Restaurant street, No. 89, Porto' },
  { id: 2, name: 'Oliver & Co.', address: 'Delight avenue, No. 2, Aveiro' },
  { id: 3, name: 'Divine Wine', address: 'Wine avenue, No. 34, Aveiro' },
  { id: 4, name: 'Green Grapes', address: 'Glass street, No. 62, Porto' },
  { id: 5, name: 'Dorothy\'s Winery', address: 'Table street, No. 5, Coimbra' },
  { id: 6, name: 'Bacchus', address: 'Greek street, No. 13, Athens' },
]

export let applications = [
  { id: 1, name: 'John Smith', age: 27 },
  { id: 2, name: 'Clara Laurence', age: 32 },
  { id: 3, name: 'Eva Thompson', age: 21 },
]

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Homepage />}></Route>
        <Route path="/staff" element={<Staff />}></Route>
        <Route path="/rider/:id" element={<RiderProfile />}></Route>
        <Route path="/tasks" element={<Tasks />}></Route>
        <Route path="/riders" element={<Riders />}></Route>
        <Route path="/stores" element={<Stores />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/applications" element={<NewApplications />}></Route>
        <Route path="/store_tasks/:id" element={<StoreTasks />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
