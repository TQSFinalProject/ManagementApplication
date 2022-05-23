// React
import { React, useState } from 'react';
import { Link, useParams, useNavigate } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import StarRating from '../components/StarRating';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Button from 'react-bootstrap/Button'
import Modal from 'react-bootstrap/Modal'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'

// CSS
import InformationSheet from '../components/css/InformationSheet.css'

// Employee data
import { staff } from '../App' 

function RiderProfile() {

    console.log(staff)
    

    const params = useParams();
    const riderId = params.id;
    const rider = staff[riderId - 1];
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    let navigate = useNavigate();

    function dismissEmployee() {
        staff.splice(riderId-1, 1) // remove rider from staff array
        navigate('/staff')
    }

    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>RIDER PROFILE</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>
                        <Link to="/staff">
                            <FontAwesomeIcon icon={faArrowLeft} style={{ fontSize: '30px', textDecoration: 'none', color: '#06113C' }} />
                        </Link>
                    </Col>
                    <Col sm={8}>
                        <Container className='informationSheet'>
                            <Row>
                                <Col className='align-self-center'>
                                    <p style={{ marginLeft: '5%' }}>
                                        <strong>ID: </strong>{riderId}<br />
                                        <strong>Name: </strong> {rider.name}<br />
                                        <strong>Rating: </strong> <StarRating rating={rider.rating} /> <br />
                                        <strong>Time as a worker: </strong> {rider.time} years<br />
                                        <strong>Work Zone: </strong> {rider.workZone}<br />
                                    </p>
                                </Col>
                                <Col className="col-xs-1" align="center">
                                    <img src={rider.img} className="rounded mr-2" alt="Employee Pic" style={{ height: '200px' }}></img>
                                </Col>
                            </Row>
                            <Row className="d-flex justify-content-center">
                                <Button style={{ marginTop: '5%', width: '20%' }} onClick={handleShow}>Dismiss</Button>
                            </Row>
                        </Container>
                    </Col>
                </Row>
            </Container>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Warning!</Modal.Title>
                </Modal.Header>
                <Modal.Body>Are you sure you want to dismiss this employee? <br/><strong>This action is permanent.</strong></Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={dismissEmployee}>
                        Dismiss
                    </Button>
                </Modal.Footer>
            </Modal>
        </>);
}

export default RiderProfile;