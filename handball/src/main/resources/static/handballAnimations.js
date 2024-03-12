function throwBall() {
    const ball = document.getElementById('ball');

    ball.style.display = 'absolute';
    ball.style.opacity = '1';
    ball.style.top = '26.5%';
    ball.style.left = '18%';
    ball.style.transform = 'translate(-50%, 0%)';

    ball.animate([
        { top: '26.5%', left: '18%', transform: 'rotate(0deg)' },
        { top: '-6.4vh', left: '50%', transform: 'rotate(1060deg)'}
    ], {
        duration: 2000,
        fill: 'forwards'
    });

    setTimeout(() => {
        ball.setAttribute("style", "top: 50% !important")
        ball.style.left = '50%';
        ball.style.transform = 'translate(-50%, -50%)';
        ball.style.opacity = '0';
        }, 4500);
}

function checkGoal() {
    const ball = document.getElementById('ball');
    const goalTop = document.getElementById('goalTop');
    const goalBottom = document.getElementById('goalBottom');

    function getBounds(element) {
        if (element) {
            const rect = element.getBoundingClientRect();
            return {
                left: rect.left + window.scrollX,
                top: rect.top + window.scrollY,
                right: rect.right + window.scrollX,
                bottom: rect.bottom + window.scrollY
            };
        }
        return null;
    }

    function ballInGoal() {
        const ballBounds = getBounds(ball);
        const goalTopBounds = getBounds(goalTop);
        const goalBottomBounds = getBounds(goalBottom);

        if (ballBounds && goalTopBounds && ballBounds.right > goalTopBounds.left && ballBounds.left < goalTopBounds.right &&
            ballBounds.bottom > goalTopBounds.top && ballBounds.top < goalTopBounds.bottom) {
            console.log('Ball in top goal!');

        } else if (ballBounds && goalBottomBounds && ballBounds.right > goalBottomBounds.left && ballBounds.left < goalBottomBounds.right &&
            ballBounds.bottom > goalBottomBounds.top && ballBounds.top < goalBottomBounds.bottom) {
            console.log('Ball in bottom goal!');
        }

        requestAnimationFrame(ballInGoal);
    }

    if (ball && goalTop && goalBottom) {
        requestAnimationFrame(ballInGoal);
    } else {
        console.error('One or more elements could not be found');
    }
}

document.addEventListener('DOMContentLoaded', checkGoal);