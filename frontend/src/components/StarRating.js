// React
import React from 'react';

// CSS
import StarRatingCSS from './css/StarRating.css'

function StarRating(props) {

    const filled_stars = props.rating
    const empty_stars = 5 - props.rating
    // console.log("RATING: " + props.rating)

    return (
        <>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />

            {[
                ...Array(filled_stars),
            ].map((value, index) => (
                <span className="fa fa-star checked" key={index}></span>
            ))}

            {[
                ...Array(empty_stars),
            ].map((value, index) => (
                <span className="fa fa-star" key={index}></span>
            ))}

        </>);
}

export default StarRating;