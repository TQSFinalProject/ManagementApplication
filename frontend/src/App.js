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
      </Routes>
    </BrowserRouter>
  );
}

export default App;
