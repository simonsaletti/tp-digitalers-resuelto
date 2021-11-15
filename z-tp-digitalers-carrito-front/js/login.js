const login = async () => {
    const params = new URLSearchParams();
    params.set('username', txtUsuario.value.trim());
    params.set('password', txtClave.value);
    params.set('grant_type', 'password');

    const credenciales = 'carrito' + ':' + '12345';
    const credencialesEncriptaoBase64 = btoa(credenciales);
    const response = await fetch('http://localhost:8081/oauth/token', {
        method: 'POST',
        body: params,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            Authorization: 'Basic ' + credencialesEncriptaoBase64,
        },
    });
    const data = await response.json();
    console.log(data);
    switch (response.status) {
        case 200:
            alert('Sesión iniciada con éxito.');
            guardarTokenSS(data.access_token);
            window.location.replace('./index.html');
            break;
        default:
            alert('Error.');
            break;
    }
};

tokenPresente();
