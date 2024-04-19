import React, { useEffect, useState } from 'react';
import './ProgressBar.scss';

const ProgressBar = () => {
    const [progress, setProgress] = useState(0);

    useEffect(() => {
        const interval = setInterval(() => {
            if (progress < 100) {
                setProgress(progress + 1);
            } else {
                clearInterval(interval);
            }
        }, 100);

        return () => clearInterval(interval);
    }, [progress]);

    return (
        <div className="progress-container">
            <div className="progress" style={{ width: `${progress}%` }}>
                {progress}%
            </div>
        </div>
    );
};

export default ProgressBar;
