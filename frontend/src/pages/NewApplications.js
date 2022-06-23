import React, { useState, useEffect } from 'react'
import { Link } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Button from 'react-bootstrap/Button'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'

import axios from "axios";

const endpoint_applications = "api/applications";

function NewApplications() {

    const [applications, setApplications] = useState([]);

    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_applications).then((response) => {
            setApplications(response.data);
        });
    }, []);

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
                                applications.map((callbackfn, idx) => (
                                    <Toast key={"key" + applications[idx].id} style={{ margin: '1%', width: '24vw' }} className="employeeCard">
                                        <Toast.Header closeButton={false}>
                                            <strong className="me-auto">Application #{applications[idx].id} </strong><br />
                                            {/* <a style={{ color: '#06113C', cursor: 'pointer' }} onClick={handleShow}><FontAwesomeIcon icon={faArrowsSpin} /></a> */}
                                        </Toast.Header>
                                        <Toast.Body>
                                            <Container>
                                                <Row>
                                                    <Col>
                                                        <span>
                                                            <strong>Name: </strong>{applications[idx].first_name + " " + applications[idx].last_name}<br />
                                                            {/* TODO: calculate age from this */}
                                                            <strong>Date of birth: </strong>{applications[idx].date_of_birth}<br />
                                                            <strong>Phone: </strong>{applications[idx].phone}<br />
                                                            <strong>E-mail: </strong>{applications[idx].email}<br />
                                                            {/* TODO: photo and cv */}
                                                        </span>
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
                        </Row>
                    </Col>
                </Row>
            </Container>
        </>
    )
}

export default NewApplications