const formatearMoneda = (monto) => {
    return (
        '$ ' +
        monto
            .toFixed(2)
            .replace(/\d(?=(\d{3})+\.)/g, '$&')
            .replace('.', ',')
    );
};

const obtenerCarritoSS = () => {
    return JSON.parse(sessionStorage.getItem('productosCarritoTemp')) || [];
};

const guardarEnCarritoSS = (elementos) => {
    sessionStorage.setItem('productosCarritoTemp', JSON.stringify(elementos));
};

const quitarDelCarritoSS = (idProducto) => {
    const carrito = obtenerCarritoSS();
    const productosRestantes = carrito.filter((producto) => producto.id != idProducto);
    guardarEnCarritoSS(productosRestantes);
};

const carritoEstaVacioSS = () => {
    return obtenerCarritoSS().length === 0;
};

const actualizarCantidadCarritoSS = (idProducto, incremento) => {
    const productosCarritoTemp = obtenerCarritoSS();
    const indiceProductoActual = productosCarritoTemp.findIndex((elemento) => idProducto === elemento.id);
    const producto = productosCarritoTemp[indiceProductoActual];
    if (Number(producto.cantidad) + incremento > Number(producto.stock)) {
        alert('No se puede realizar acción por falta de stock!');
        return;
    }
    let cantidadNueva;
    if (Number(producto.cantidad) === 1 && incremento < 0) {
        cantidadNueva = Number(producto.cantidad);
    } else {
        cantidadNueva = Number(producto.cantidad) + incremento;
    }
    const productoActualizado = { ...producto, cantidad: cantidadNueva };
    productosCarritoTemp[indiceProductoActual] = productoActualizado;
    guardarEnCarritoSS(productosCarritoTemp);
};

const calcularTotalCarritoSS = () => {
    const productos = obtenerCarritoSS();
    const subtotales = productos.map((producto) => producto.cantidad * producto.precio);
    const totalCarrito = subtotales.reduce((a, b) => a + b);
    return totalCarrito;
};

const obtenerProductoSS = (idProducto) => {
    const productos = obtenerCarritoSS();
    const productoFiltrado = productos.find((producto) => producto.id === idProducto);
    return productoFiltrado;
};

const obtenerParesProductoCantidadSS = () => {
    const productos = obtenerCarritoSS();
    const pares = productos.map((producto) => {
        return {
            idProducto: producto.id,
            cantidad: producto.cantidad,
        };
    });
    return pares;
};

const existeURLParam = (paramBuscado) => {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.has(paramBuscado);
};

const obtenerValorURLParam = (paramBuscado) => {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get(paramBuscado);
};

const vaciarCarritoSS = () => {
    sessionStorage.removeItem('productosCarritoTemp');
};
