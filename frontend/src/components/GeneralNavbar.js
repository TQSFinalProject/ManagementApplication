import React from 'react';

// CSS
import './css/GeneralNavbar.css'

function GeneralNavbar() {
    return <>
        <nav className="navMenu">
            <a href="/">Home</a>
            <a href="/staff">Staff</a>
            <a href="/tasks">Tasks</a>
            <a href="/riders">Riders</a>
            <a href="/stores">Stores</a>
            <div className="dot"></div>
        </nav>
    </>;
}

export default GeneralNavbar;