import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { setCartAsync, setCheckoutOrder } from '@/data/redux/actions/cartActions';
import BackEndServiceLocations from './backEndServiceLocations';
import { addOrUpdateOrder } from '@/data//redux/actions/ordersActions';

const AsyncChannel = ({userId}) => {

    var ws = new WebSocket(BackEndServiceLocations.getLocation("NOTIFICATION_SERVICE") + "/notification/ws");

    //console.log("AsyncChannel() userId is:", userId);

    const dispatch = useDispatch();

    const handleMessageFrame = (frame) => {
        //console.log("handleMessageFrame() frame",frame);
        const splitFrame = frame.split(/\r?\n/);
        const message = splitFrame[splitFrame.length - 1].replace(/\0/g, '');
        //console.log("handleMessageFrame() message",message);
        const messageBody = JSON.parse(message);
        //console.log("handleMessageFrame() messageBody",messageBody);
        console.log("AsyncChannel() got message:", JSON.stringify(JSON.parse(message), null, "\t"));
        switch (messageBody.notificationType) {
            case "CheckoutSuccessfulNotification":
                //console.log("AsyncChannel() CheckoutSuccessfulNotification basketItems:", messageBody.basket.basketItems);
                dispatch(setCartAsync(messageBody.basket.basketItems));
                break;
            case "OrderVerifiedNotification":
                //console.log("AsyncChannel() OrderVerifiedNotification order:", messageBody.order);
                dispatch(setCheckoutOrder(messageBody.order));
                dispatch(addOrUpdateOrder(messageBody.order));
                break;
            case "OrderPaymentSucceededNotification":
                //console.log("AsyncChannel() OrderPaymentSucceededNotification order:", messageBody.order);
                dispatch(addOrUpdateOrder(messageBody.order));
                break;
            default:
                console.log("doing nothing for notificationType", messageBody.notificationType);
                break;
        }
    };

    ws.onopen = () => {
        // connection opened
        //console.log("AsyncChannel() onopen()");
        ws.send(
            new TextEncoder().encode(
                'CONNECT\n' + 
                'accept-version:1.2,1.1,1.0\n' + 
                'heart-beat:10000,10000\n' + 
                '\n\x00'
            )
        );
    };

    const handleConnectedFrame = () => {
        //console.log("handleConnectedFrame()");
        // just send the subscribe request
        ws.send(
            new TextEncoder().encode(
                'SUBSCRIBE\n' + 
                'id:sub-0\n' + 
                'destination:/queue/' + userId + '\n' + 
                '\n\x00'
            )
        ); 
    }

    ws.onmessage = (m) => {
      // a message was received
      //console.log("AsyncChannel() onmessage()");
      //console.log(m.data);
      //console.log("message type is",m.data.split(/\r?\n/)[0]);
      const frameType = m.data.split(/\r?\n/)[0];
      switch (frameType) {
        case "CONNECTED":
            handleConnectedFrame();
            break;
        case "MESSAGE":
            handleMessageFrame(m.data);
            break;
        default:
            console.log("doing nothing for frameType",frameType,"data",m.data);
            break;
      }
    };

    ws.onerror = (e) => {
      // an error occurred
      console.log("AsyncChannel() onerror()");
      console.log(e.message);
    };

    ws.onclose = (e) => {
      // connection closed
      //console.log("AsyncChannel() onclose()");
      console.log(e.code, e.reason);
    };
    
    return (<></>);
}

export default AsyncChannel;