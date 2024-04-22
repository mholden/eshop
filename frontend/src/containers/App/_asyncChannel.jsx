import React from 'react';
import { useSubscription } from "react-stomp-hooks";

const AsyncChannelLogginIn = ({userId}) => {

    //console.log("AsyncChannelLogginIn userId is:", userId);
    
    useSubscription("/queue/" + userId, (message) => {
        console.log("AsyncChannel got message:", JSON.stringify(JSON.parse(message.body), null, "\t"));
        // TODO: handle all async messages here
    });

    return (
        <> </>
    );
}

export default AsyncChannelLogginIn;