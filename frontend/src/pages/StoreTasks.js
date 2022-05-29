import React from 'react'
import { Link, useParams } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import Pagination from '../components/Pagination';

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'

import { staff, tasks, stores } from '../App'
import { secsToMins } from './Tasks';

export function StoreTasks() {

    const params = useParams();
    const storeId = params.id;

    const collect = require('collect.js');

    let storeTasks = collect(tasks).where('storeId', '=', stores[storeId - 1].id).items
    console.log(storeTasks)

    function handleCallback(page) { // for the pagination buttons

    }

    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>STORE TASKS</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>
                        <Link to="/stores">
                            <FontAwesomeIcon icon={faArrowLeft} style={{ fontSize: '30px', textDecoration: 'none', color: '#06113C' }} />
                        </Link>
                    </Col>
                    <Col sm={8}>
                        {storeTasks.length != 0 ?
                            <>
                                <Row className="d-flex justify-content-center">
                                    {storeTasks.map((callbackfn, idx) => (
                                        <Toast key={"key" + storeTasks[idx].id} style={{ margin: '1%', width: '24vw' }} className="employeeCard">
                                            <Toast.Header closeButton={false}>
                                                <strong className="me-auto">Task #{storeTasks[idx].id} </strong><br />
                                            </Toast.Header>
                                            <Toast.Body>
                                                <Container>
                                                    <Row>
                                                        <Col>
                                                            <span>
                                                                <strong>Rider: </strong>{staff[storeTasks[idx].riderId - 1].name}<br />
                                                                <strong>Store: </strong>{stores[storeTasks[idx].storeId - 1].name}<br />
                                                                <strong>Delivery address: </strong>{storeTasks[idx].delivery}<br />
                                                                <strong>Distance from store: </strong>{storeTasks[idx].distance} km<br />
                                                                <strong>Submitted: </strong>{secsToMins(storeTasks[idx].time)} ago<br />
                                                                <strong>Estimated completion time: </strong>{secsToMins(storeTasks[idx].completion)}<br />
                                                            </span>
                                                        </Col>
                                                    </Row>
                                                </Container>
                                            </Toast.Body>
                                        </Toast>
                                    ))}
                                </Row>
                                <Row className="d-flex justify-content-center">
                                    {/* 4 tasks per page: TODO: elements per page as pagination input */}
                                    <Pagination pageNumber={1} parentCallback={handleCallback} />
                                </Row>
                            </>
                            :
                            <>
                                <Container>
                                    <Row>
                                        <Col className='align-self-center col-xs-1' align='center'>
                                            <p>This store has no tasks currently.</p>
                                        </Col>
                                    </Row>
                                </Container>
                            </>
                        }

                    </Col>
                </Row>
            </Container>
        </>);
}

export default StoreTasks