// React
import { React, useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Button from 'react-bootstrap/Button'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faListCheck } from '@fortawesome/free-solid-svg-icons'

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import Pagination from '../components/Pagination';

const endpoint_stores = "api/stores";
const endpoint_tasksPerStore = "api/orders/store/";

function Stores() {

    const [stores, setStores] = useState([]);

    const [totalPages, setTotalPages] = useState(0);

    const [tasksPerStore, setTasksPerStore] = useState({});

    let navigate = useNavigate();

    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_stores + "?page=" + 0).then((response) => {
            setStores(response.data.content);
            setTotalPages(response.data.totalPages);
        });
    }, []);

    useEffect(() => {
        for (let store of stores) {
            axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_tasksPerStore + store.id).then((response) => {
                let storeKey = "store" + store.id;
                let newTaskNum = {};
                newTaskNum[storeKey] = response.data.totalElements;

                setTasksPerStore(tasksPerStore => ({
                    ...tasksPerStore,
                    ...newTaskNum
                }));
            });
        }
    }, [stores]);

    function handleCallback(page) {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_stores + "?page=" + (page - 1)).then((response) => {
            setStores(response.data.content);
        });
    }

    function redirectStoreTasksPage(storeId) {
        navigate('/store_tasks/' + storeId);
    }

    console.log(tasksPerStore);

    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>AFFILIATED STORES</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                {stores.length == 0 ?
                    <Row>
                        <h5 style={{ textAlign: 'center' }}>There are no affiliated stores.</h5>
                    </Row>
                    :
                    <Row>
                        <Col sm={4}>

                            <p><strong>Filters:</strong></p>

                            <label htmlFor="searchAssignedRider" className="inp">
                                <input type="text" id="searchAssignedRider" placeholder="&nbsp;" />
                                <span className="label">Name</span>
                                <span className="focus-bg"></span>
                            </label>

                            <label htmlFor="searchStore" className="inp">
                                <input type="text" id="searchStore" placeholder="&nbsp;" />
                                <span className="label">Address</span>
                                <span className="focus-bg"></span>
                            </label>

                        </Col>
                        <Col sm={8}>
                            <Row className="d-flex justify-content-center">
                                {stores.map((callbackfn, idx) => (
                                    <Toast key={"key" + stores[idx].id} style={{ margin: '1%', width: '22vw' }} className="employeeCard">
                                        <Toast.Header closeButton={false}>
                                            <strong className="me-auto">Store #{stores[idx].id} </strong><br />
                                        </Toast.Header>
                                        <Toast.Body>
                                            <Container>
                                                <Row>
                                                    <Col>
                                                        <span>
                                                            <strong>Name: </strong>{stores[idx].storeName}<br />
                                                            <strong>Address: </strong>{stores[idx].storeAddress}<br />
                                                            <strong>Shipping tax: </strong>{stores[idx].shippingTax}%<br />
                                                            <strong>Current tasks: </strong>{tasksPerStore["store" + stores[idx].id]}<br />
                                                        </span>
                                                    </Col>
                                                </Row>
                                                <Row>
                                                    <Col className="d-flex justify-content-center">
                                                        <Button style={{ marginTop: '3%' }} onClick={() => { redirectStoreTasksPage(stores[idx].id) }}><FontAwesomeIcon icon={faListCheck} /></Button>
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

export default Stores;