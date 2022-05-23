// React
import React from 'react';
// import { Link } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import ModelViewer from '../components/ModelViewer';

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

            <Container>
                <Row>
                    <Col sm={8}>
                        <div style={{ marginTop: '2%', width: '30vw', height: '89vh' }}>
                            <ModelViewer scale="0.4" modelPath={"/wine_model/scene.gltf"} />
                        </div>
                    </Col>
                    <Col sm={4}>
                        <span className='mainTitle'>CHATEAU DU VIN</span>
                    </Col>
                </Row>
            </Container>
        </>);
}

export default Homepage;