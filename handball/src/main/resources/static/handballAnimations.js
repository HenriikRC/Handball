const throwingPlayer = 'AWAY_RIGHT_WING';
const opponentPlayer = 'HOME_KEEPER';
const assistingPlayer = null;

function throwBall() {
    const ball = document.getElementById('ball');
    const player = document.getElementById(throwingPlayer);
    const field = document.querySelector('.field'); // Assuming the players are within this container

    if (!player || !field) {
        console.error("Throwing player or field not found:", throwingPlayer);
        return;
    }

    // Get bounding rectangles
    const playerRect = player.getBoundingClientRect();
    const fieldRect = field.getBoundingClientRect();

    // Calculate player's position relative to the field, not the entire window
    const playerXRelativeToField = ((playerRect.left + playerRect.right) / 2) - fieldRect.left;
    const playerYRelativeToField = ((playerRect.top + playerRect.bottom) / 2) - fieldRect.top;

    // Convert these positions to percentages of the field's dimensions
    const playerXPercentage = `${(playerXRelativeToField / fieldRect.width) * 100}%`;
    const playerYPercentage = `${(playerYRelativeToField / fieldRect.height) * 100}%`;

    ball.style.position = 'absolute';
    ball.style.opacity = '1';
    ball.style.top = playerYPercentage;
    ball.style.left = playerXPercentage;
    // Adjusting transform to center the ball on the calculated position
    ball.style.transform = 'translate(-50%, -50%)';

    const keyframes = [
        { top: playerYPercentage, left: playerXPercentage, transform: 'rotate(0deg)'},
        { top: '-6.4vh', left: '50%', transform: 'rotate(1060deg)'}
    ];

    ball.animate(keyframes, {
        duration: 2000,
        fill: 'forwards'
    });

    setTimeout(() => {
        ball.setAttribute("style", "top: 50% !important");
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