document.getElementById('runButton').addEventListener('click', async () => {
    const response = await fetch('a.out.wasm');
    const bytes = await response.arrayBuffer();
    const module = await WebAssembly.compile(bytes);
    const instance = await WebAssembly.instantiate(module);

    // Call a function from the WebAssembly module
    const result = instance.exports.yourFunctionName(/* parameters */);
    
    document.getElementById('result').innerText = `Result: ${result}`;
});
