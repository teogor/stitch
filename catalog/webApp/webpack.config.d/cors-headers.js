;(function(config) {
    // Required for SharedArrayBuffer / OPFS used by @sqlite.org/sqlite-wasm
    config.devServer = config.devServer || {};
    config.devServer.headers = [
        { key: 'Cross-Origin-Opener-Policy', value: 'same-origin' },
        { key: 'Cross-Origin-Embedder-Policy', value: 'require-corp' }
    ];
})(config);
