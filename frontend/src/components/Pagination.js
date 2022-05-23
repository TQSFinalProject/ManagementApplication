// React
import React from 'react';

// CSS
import PaginationCSS from './css/Pagination.css'

function Pagination(props) { // props.pageNumber

    window.onload = function () { // starts on page 1 by default
        pageChange(1)
    };

    function pageChange(pageNumber) {
        console.log(pageNumber)
        for (let i = 1; i <= props.pageNumber; i++) {
            document.getElementById("page" + i).classList.remove("active")
        }
        document.getElementById("page" + pageNumber).classList.add("active")
        props.parentCallback(pageNumber);
    }

    function shiftPage(direction) {
        for (let i = 1; i <= props.pageNumber; i++) {
            if (document.getElementById("page" + i).classList.contains("active")) {
                let newPage;
                if (direction == -1) { // backwards
                    newPage = (i - 10) < 1 ? 1 : i - 10;
                }
                else { // forwards
                    newPage = (i + 10) > props.pageNumber ? props.pageNumber : i + 10;
                }

                if (newPage) {
                    document.getElementById("page" + i).classList.remove("active");
                    document.getElementById("page" + newPage).classList.add("active");
                    props.parentCallback(newPage);
                }
                break;
            }

        }
    }

    return (
        <>
            <div className="pagination" style={{ marginTop: '5%', marginBottom: '5%' }}>
                <a className="clickable" onClick={() => { shiftPage(-1) }}>&laquo;</a>
                <a id="page1" className="active clickable" onClick={() => { pageChange(1) }}>1</a>

                {[
                    ...Array(props.pageNumber).slice(1),
                ].map((value, index) => (
                    <a id={"page" + (index + 2)} className="clickable" key={index} onClick={() => { pageChange(index + 2) }}>{index + 2}</a>
                ))}

                <a className="clickable" onClick={() => { shiftPage(1) }}>&raquo;</a>
            </div>
        </>);
}

export default Pagination;