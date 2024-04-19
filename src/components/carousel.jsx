import React, { useState } from 'react';
import { Carousel } from 'antd';

const MyCarousel = ({ images }) => {
  const [currentSlide, setCurrentSlide] = useState(0);

  const handleSlideChange = (current) => {
    setCurrentSlide(current);
  };

  return (
    <div className="carousel-container" style={{ margin: 5 }}>
      <Carousel afterChange={handleSlideChange} autoplay>
        {images.map((image, index) => (
          <div key={index}>
            <img src={image} alt={`Slide ${index}`} style={{ width: '100%', height: '100%' }} />
          </div>
        ))}
      </Carousel>
    </div>
  );
};

export default MyCarousel;
