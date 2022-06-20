import React, { useState, useEffect } from 'react'
import { Link } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import Pagination from '../components/Pagination';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Button from 'react-bootstrap/Button'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'
import { faFile } from '@fortawesome/free-solid-svg-icons'

import axios from "axios";

const endpoint_applications = "api/jobApplications";

function getAge(birthDate) {
    const dateElements = birthDate.split("-");
    const birthYear = parseInt(dateElements[0]);
    const currentYear = new Date().getFullYear();
    const age = currentYear - birthYear;
    return age;
}

function NewApplications() {

    const [applications, setApplications] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_applications + "?page=" + 0).then((response) => {
            setApplications(response.data.content);
            setTotalPages(response.data.totalPages);
        });
    }, []);

    function handleCallback(page) {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_applications + "?page=" + (page-1)).then((response) => {
            setApplications(response.data.content);
        });
    }

    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>NEW JOB APPLICATIONS</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>
                        <Link to="/staff">
                            <FontAwesomeIcon icon={faArrowLeft} style={{ fontSize: '30px', textDecoration: 'none', color: '#06113C' }} />
                        </Link>
                    </Col>
                    <Col sm={8}>
                        <Row className="d-flex justify-content-center">
                            {applications.length == 0 ?

                                <h5>No applications currently.</h5>
                                :
                                <>
                                    {applications.map((callbackfn, idx) => (
                                        <Toast key={"key" + applications[idx].id} style={{ margin: '1%', width: '22vw' }} className="employeeCard">
                                            <Toast.Header closeButton={false}>
                                                <strong className="me-auto">Application #{applications[idx].id} </strong><br />
                                                <Button target="_blank" href={applications[idx].cv}><FontAwesomeIcon icon={faFile} /></Button>
                                            </Toast.Header>
                                            <Toast.Body>
                                                <Container>
                                                    <Row>
                                                        <Col>
                                                            <span>
                                                                <strong>Name: </strong>{applications[idx].firstName + " " + applications[idx].lastName}<br />
                                                                <strong>Age: </strong>{getAge(applications[idx].dateOfBirth)}<br />
                                                                <strong>Phone: </strong>{applications[idx].phone}<br />
                                                                <strong>E-mail: </strong>{applications[idx].email}<br />
                                                            </span>
                                                        </Col>
                                                        <Col className='align-self-center col-xs-1' align='center'>
                                                            <img src={applications[idx].photo} className="rounded mr-2" alt="Employee Pic" style={{ height: '80px' }}></img>
                                                        </Col>
                                                    </Row>
                                                    <Row style={{ marginTop: '5%' }}>
                                                        <Col className='align-self-center col-xs-1' align='center'>
                                                            <Button className='acceptApplication' style={{ marginRight: '1%' }} >Accept</Button>
                                                            <Button className='dismissApplication' style={{ marginLeft: '1%' }} >Dismiss</Button>
                                                        </Col>
                                                    </Row>
                                                </Container>
                                            </Toast.Body>
                                        </Toast>
                                    ))}
                                </>
                            }
                        </Row>

                        {applications.length == 0 ?
                            <></>
                            :
                            <Row className="d-flex justify-content-center">
                                <Pagination pageNumber={totalPages} parentCallback={handleCallback} />
                            </Row>
                        }
                    </Col>
                </Row>
            </Container>
        </>
    )
}

export default NewApplications