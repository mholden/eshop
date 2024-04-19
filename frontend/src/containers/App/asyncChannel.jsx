import React, { useEffect } from 'react';
import AuthService from '../../services/AuthService';
import AsyncChannelLogginIn from './_asyncChannel';

const AsyncChannel = () => {

    // TODO: keycloack hook

    useEffect(() => {
        if (AuthService.isLoggedIn()) {
            console.log("AsyncChannel logged in, getting user info");
            AuthService.loadUserInfo().then((data) => {
                console.log("AsyncChannel userInfo is:", data);
            });
        }
    }, []);

    return (
        <>
        {
          AuthService.isLoggedIn() && 
          <AsyncChannelLogginIn userId={'pass-userInfo.sub-here'} />
        }
        </>
    );
}

export default AsyncChannel;