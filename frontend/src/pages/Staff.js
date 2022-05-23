// React
import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import StarRating from '../components/StarRating';
import Pagination from '../components/Pagination';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Dropdown from 'react-bootstrap/Dropdown'
import Button from 'react-bootstrap/Button'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUser } from '@fortawesome/free-solid-svg-icons'

// CSS
import DropdownCSS from '../components/css/Dropdowns.css'

// Employee data
import { staff } from '../App'

function dynamicSort(property) {
    var sortOrder = 1;
    if (property[0] === "-") {
        sortOrder = -1;
        property = property.substr(1);
    }
    return function (a, b) {
        var result = (a[property] < b[property]) ? -1 : (a[property] > b[property]) ? 1 : 0;
        return result * sortOrder;
    }
}

function Staff() {

    // document.getElementById("page1").click();

    let navigate = useNavigate();
    let local_staff = [...staff]; // because the sorting of the filters was changing the original staff and messing things up

    function handleCallback(page) { // for the pagination buttons
        setCurrentPage(page);
    }

    function redirectUserPage(userId) {
        navigate('/rider/' + userId);
    }

    let [currentPage, setCurrentPage] = useState(null);
    let [orderedStaff, setOrderedStaff] = useState(local_staff);

    function staffList() {
        return orderedStaff.slice(((currentPage - 1) * 6), (currentPage * 6));
    }

    function checkBoxFoolery() {

        local_staff = [...staff]

        let a = document.getElementById('checkboxA').checked == true
        let b = document.getElementById('checkboxB').checked == true
        let c = document.getElementById('checkboxC').checked == true
        let d = document.getElementById('checkboxD').checked == true

        if (a || b || c || d) {
            if (!a) {
                for (let driver of local_staff) {
                    if (driver.workZone=='A') {
                        
                    }
                }
            }
        }
        // else {
        //     local_staff = [...staff]
        // }

    }

    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>PERSONNEL MANAGEMENT</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>

                        <p><strong>Filters:</strong></p>

                        <Dropdown className='filterDropdown'>
                            <Dropdown.Toggle id="dropdown-basic">
                                Rating
                            </Dropdown.Toggle>
                            <Dropdown.Menu>
                                <Dropdown.Item className="clickable" onClick={() => { setOrderedStaff(local_staff.sort(dynamicSort("rating"))) }}>1-5</Dropdown.Item>
                                <Dropdown.Item className="clickable" onClick={() => { setOrderedStaff(local_staff.sort(dynamicSort("-rating"))) }}>5-1</Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>

                        <Dropdown className='filterDropdown'>
                            <Dropdown.Toggle id="dropdown-basic">
                                Alphabetical Order
                            </Dropdown.Toggle>
                            <Dropdown.Menu>
                                <Dropdown.Item className="clickable" onClick={() => { setOrderedStaff(local_staff.sort(dynamicSort("name"))) }}>A-Z</Dropdown.Item>
                                <Dropdown.Item className="clickable" onClick={() => { setOrderedStaff(local_staff.sort(dynamicSort("-name"))) }}>Z-A</Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>

                        <Dropdown className='filterDropdown'>
                            <Dropdown.Toggle id="dropdown-basic">
                                Time As An Employee
                            </Dropdown.Toggle>
                            <Dropdown.Menu>
                                <Dropdown.Item className="clickable" onClick={() => { setOrderedStaff(local_staff.sort(dynamicSort("time"))) }}>Up</Dropdown.Item>
                                <Dropdown.Item className="clickable" onClick={() => { setOrderedStaff(local_staff.sort(dynamicSort("-time"))) }}>Down</Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>

                        <div className='checkboxGroup' style={{ verticalAlign: 'middle', marginTop: '3%' }}>
                            <p>Work Zone:</p>
                            <label className="container">
                                <input id='checkboxA' type="checkbox" onChange={checkBoxFoolery} />
                                &nbsp; A
                            </label>
                            <label className="container">
                                <input id='checkboxB' type="checkbox" onChange={checkBoxFoolery} />
                                &nbsp; B
                            </label>
                            <label className="container">
                                <input id='checkboxC' type="checkbox" onChange={checkBoxFoolery} />
                                &nbsp; C
                            </label>
                            <label className="container">
                                <input id='checkboxD' type="checkbox" onChange={checkBoxFoolery} />
                                &nbsp; D
                            </label>
                        </div>

                    </Col>
                    <Col sm={8}>
                        <Row className="d-flex justify-content-center">
                            {staffList().map((callbackfn, idx) => (
                                <Toast key={"key" + staffList()[idx].id} style={{ margin: '1%', width: '20vw' }} className="employeeCard">
                                    <Toast.Header closeButton={false}>
                                        <strong className="me-auto">Employee #{staffList()[idx].id} </strong>
                                    </Toast.Header>
                                    <Toast.Body>
                                        <Container>
                                            <Row>
                                                <Col className='align-self-center col-xs-1'  align='center'>
                                                    {staffList()[idx].name}<br />
                                                    <StarRating rating={staffList()[idx].rating} />

                                                </Col>
                                                <Col className='align-self-center col-xs-1'  align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                                                    <img src={staffList()[idx].img} className="rounded mr-2" alt="Employee Pic" style={{ height: '50px' }}></img>                                                </Col>
                                                <Col className='align-self-center col-xs-1'  align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                                                    <Button onClick={() => { redirectUserPage(staffList()[idx].id) }}><i className="fa fa-user"></i></Button>
                                                </Col>
                                            </Row>
                                        </Container>
                                    </Toast.Body>
                                </Toast>
                            ))}
                        </Row>
                        <Row className="d-flex justify-content-center">
                            {/* 6 employees per page */}
                            <Pagination pageNumber={Math.ceil(local_staff.length / 6)} parentCallback={handleCallback} />
                        </Row>
                    </Col>
                </Row>
            </Container>
        </>);
}

export default Staff;