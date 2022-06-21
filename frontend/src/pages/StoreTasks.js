import React, { useState, useEffect } from 'react'
import { Link, useParams } from "react-router-dom";
import axios from "axios";

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
import Badge from 'react-bootstrap/Badge'

const endpoint_tasksPerStore = "api/orders/store/";
const endpoint_riders = "api/riders";
const endpoint_stores = "api/stores";

export function StoreTasks() {

    const params = useParams();
    const storeId = params.id;

    const [storeTasks, setStoreTasks] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    const [ridernames, setRidernames] = useState({});
    const [storename, setStorename] = useState("");

    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_tasksPerStore + storeId + "?page=" + 0).then((response) => {
            setStoreTasks(response.data.content);
            setTotalPages(response.data.totalPages);
        });
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_stores + "/" + storeId).then((response) => {
            setStorename(response.data.storeName);
        });
    }, []);

    useEffect(() => {
        for (let task of storeTasks) {
            let riderId = task.riderId;

            axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_riders + "/" + riderId).then((response) => {
                let riderKey = "rider" + riderId;
                let newRidername = {};
                newRidername[riderKey] = response.data.firstName + " " + response.data.lastName;

                setRidernames(ridernames => ({
                    ...ridernames,
                    ...newRidername
                }));
            });
        }

    }, [storeTasks]);

    function handleCallback(page) {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_tasksPerStore + storeId + "?page=" + (page-1)).then((response) => {
            setStoreTasks(response.data.content);
        });
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
                                        <Toast key={"key" + storeTasks[idx].id} style={{ margin: '1%', width: '22vw' }} className="employeeCard">
                                            <Toast.Header closeButton={false}>
                                                <strong className="me-auto">Task #{storeTasks[idx].id} </strong><br />
                                                {storeTasks[idx].orderStatus == 'Late' ?
                                                    <Badge style={{ marginRight: '5%' }} bg="danger">Late</Badge>
                                                    :
                                                    <></>
                                                }
                                            </Toast.Header>
                                            <Toast.Body>
                                                <Container>
                                                    <Row>
                                                        <Col>
                                                            <span>
                                                                <strong>Rider: </strong>{ridernames["rider" + storeTasks[idx].riderId]}<br />
                                                                <strong>Store: </strong>{storename}<br />
                                                                <strong>Delivery address: </strong>{storeTasks[idx].deliveryAddress}<br />
                                                            </span>
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