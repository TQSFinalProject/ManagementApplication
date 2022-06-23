// React
import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from "react-router-dom";

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

// // Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faM, faMap } from '@fortawesome/free-solid-svg-icons'
import { faFile } from '@fortawesome/free-solid-svg-icons'

// CSS
import DropdownCSS from '../components/css/Dropdowns.css'

import axios from "axios";

const endpoint_riders = "api/riders";

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

function averageRating(listOfRatings) {
    let sum = 0;
    let count = 0;
    for (let rating of listOfRatings) {
        count += 1;
        sum += rating;
    }
    let avg = Math.round(sum / count);
    return avg;
}

function Staff() {

    let navigate = useNavigate();

    const [staff, setStaff] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_riders + "?page=" + 0).then((response) => {
            setStaff(response.data.content);
            setTotalPages(response.data.totalPages);
        });
    }, []);

    function handleCallback(page) {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_riders + "?page=" + (page-1)).then((response) => {
            setStaff(response.data.content);
        });
    }

    function redirectUserPage(userId) {
        navigate('/rider/' + userId);
    }

    return (
        <>
            <GeneralNavbar />

            <Container>
                <Row>
                    <Col style={{ marginTop: '5%' }} sm={8}>
                        <h1>PERSONNEL MANAGEMENT</h1>
                    </Col>
                    <Col className="text-center text-md-right" style={{ marginTop: '10%' }} sm={4}>
                        <Link to='/map'>
                            <Button style={{ marginRight: '3%' }}><FontAwesomeIcon icon={faMap} /></Button>
                        </Link>
                        <Link to='/applications'>
                            <Button><FontAwesomeIcon icon={faFile} /></Button>
                        </Link>
                    </Col>
                </Row>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                {staff.length == 0 ?
                    <Row>
                        <h5 style={{ textAlign: 'center' }}>There is no hired staff.</h5>
                    </Row>
                    :
                    <Row>
                        <Col sm={4}>

                            <p><strong>Filters:</strong></p>

                            <Dropdown className='filterDropdown'>
                                <Dropdown.Toggle id="dropdown-basic">
                                    Rating
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    {/* setOrderedStaff(local_staff.sort(dynamicSort("rating"))) */}
                                    <Dropdown.Item className="clickable" onClick={() => { }}>1-5</Dropdown.Item>
                                    <Dropdown.Item className="clickable" onClick={() => { }}>5-1</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>

                            <Dropdown className='filterDropdown'>
                                <Dropdown.Toggle id="dropdown-basic">
                                    Alphabetical Order
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    <Dropdown.Item className="clickable" onClick={() => { }}>A-Z</Dropdown.Item>
                                    <Dropdown.Item className="clickable" onClick={() => { }}>Z-A</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>

                            {/* <Dropdown className='filterDropdown'>
                                <Dropdown.Toggle id="dropdown-basic">
                                    Time As An Employee
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    <Dropdown.Item className="clickable" onClick={() => { }}>Up</Dropdown.Item>
                                    <Dropdown.Item className="clickable" onClick={() => { }}>Down</Dropdown.Item>
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
                            </div> */}

                        </Col>
                        <Col sm={8}>
                            <Row className="d-flex justify-content-center">
                                {staff.map((callbackfn, idx) => (
                                    <Toast key={"key" + staff[idx].id} style={{ margin: '1%', width: '20vw' }} className="employeeCard">
                                        <Toast.Header closeButton={false}>
                                            <strong className="me-auto">Employee #{staff[idx].id} </strong>
                                        </Toast.Header>
                                        <Toast.Body>
                                            <Container>
                                                <Row>
                                                    <Col className='align-self-center col-xs-1' align='center'>
                                                        {staff[idx].firstName + " " + staff[idx].lastName}<br />
                                                        <StarRating rating={staff[idx].ratingMean} />

                                                    </Col>
                                                    <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                                                        <img src='https://cdn-icons-png.flaticon.com/512/147/147144.png' className="rounded mr-2" alt="Employee Pic" style={{ height: '50px' }}></img>
                                                    </Col>
                                                    <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                                                        <Button onClick={() => { redirectUserPage(staff[idx].id) }}><i className="fa fa-user"></i></Button>
                                                    </Col>
                                                </Row>
                                            </Container>
                                        </Toast.Body>
                                    </Toast>
                                ))}
                            </Row>
                            <Row className="d-flex justify-content-center">
                                <Pagination pageNumber={totalPages} parentCallback={handleCallback} />
                            </Row>
                        </Col>
                    </Row>
                }
            </Container>
        </>);
}

export default Staff;