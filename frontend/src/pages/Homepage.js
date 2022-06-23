// React
import React from 'react';
import { Link } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'

// CSS
import '../components/css/HomepageTitle.css'

function Homepage() {

    return (
        <>
            <GeneralNavbar />

            <Container style={{ marginInline: 'auto' }}>
                <Row className="d-flex justify-content-center">
                    <Col className='align-self-center col-xs-1' align='center'>
                        <div style={{ marginTop: '40%' }}>
                            <svg xmlns="http://www.w3.org/2000/svg">
                                <filter id="motion-blur-filter" filterUnits="userSpaceOnUse">
                                    <feGaussianBlur stdDeviation="100 0"></feGaussianBlur>
                                </filter>
                            </svg>
                            <span>
                                <span className='homepageTitle' filter-content="T">TRACK</span> <br />
                                <span className='homepageTitle' filter-content="I">IT</span>
                            </span>
                        </div>
                        <Row style={{ marginTop: '5%' }}>
                            <span className='presentationText'>A trustworthy management system.</span>
                        </Row>
                    </Col>
                    <Col className='align-self-center col-xs-1' align='center'>
                        <Link to='/staff'>
                            <button className="learn-more" style={{ marginTop: '70%' }}>
                                <span className="circle" aria-hidden="true">
                                    <span className="icon arrow"></span>
                                </span>
                                <span className="button-text">Check your staff</span>
                            </button>
                        </Link>
                    </Col>
                </Row>
            </Container>
        </>);
}

export default Homepage;