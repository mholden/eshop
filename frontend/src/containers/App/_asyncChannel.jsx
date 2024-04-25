import React from 'react';
import { useDispatch } from 'react-redux';
import { useSubscription } from "react-stomp-hooks";
import { setCartAsync } from '../../redux/actions/cartActions';

const AsyncChannelLogginIn = ({userId}) => {

    //console.log("AsyncChannelLogginIn userId is:", userId);

    const dispatch = useDispatch();

    useSubscription("/queue/" + userId, (message) => {
        const messageBody = JSON.parse(message.body);
        console.log("AsyncChannel got message:", JSON.stringify(JSON.parse(message.body), null, "\t"));
        switch (messageBody.notificationType) {
            case "CheckoutSuccessfulNotification":
                console.log("CheckoutSuccessfulNotification basketItems:", messageBody.basket.basketItems);
                dispatch(setCartAsync(messageBody.basket.basketItems));
                break;
            default:
                console.log("doing nothing for notificationType", messageBody.notificationType);
                break;
        }
    });

    return (
        <> </>
    );
}

export default AsyncChannelLogginIn;