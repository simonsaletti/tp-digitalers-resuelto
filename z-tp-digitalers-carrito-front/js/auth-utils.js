const guardarTokenSS = (accessToken) => {
    sessionStorage.setItem('token', accessToken);
};

const eliminarTokenSS = () => {
    sessionStorage.removeItem('token');
    vaciarCarritoSS();
    window.location.replace('./login.html');
};

const obtenerTokenSS = () => {
    return sessionStorage.getItem('token');
};

const obtenerDatosToken = (accessToken) => {
    if (accessToken === null || accessToken === undefined) {
        return null;
    }
    return JSON.parse(atob(accessToken.split('.')[1]));
};

const tokenPresente = () => {
    const accessToken = obtenerTokenSS();
    const tokenInfo = obtenerDatosToken(accessToken);
    if (tokenInfo !== null && tokenInfo.user_name && tokenInfo.user_name.length > 0) {
        if (window.location.pathname === '/login.html') {
            window.location.replace('./index.html');
        }
    } else {
        if (window.location.pathname !== '/login.html') {
            window.location.replace('./login.html');
        }
    }
};

const tokenValido = (response) => {
    if (response != null && response.error === 'invalid_token') {
        eliminarTokenSS();
        return false;
    }
    return true;
};

const obtenerTokenAuthorizationHeader = (headers) => {
    if (obtenerTokenSS()) {
        headers.append('Authorization', `Bearer ${obtenerTokenSS()}`);
    }
    return headers;
};
