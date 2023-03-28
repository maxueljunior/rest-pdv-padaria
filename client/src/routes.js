import React from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Login from './pages/Login';
import Cliente from './pages/Cliente'

export default function AppRoutes(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login/>}/>
                <Route path="/cliente" element={<Cliente/>}/>
            </Routes>
        </BrowserRouter>
    );
}
