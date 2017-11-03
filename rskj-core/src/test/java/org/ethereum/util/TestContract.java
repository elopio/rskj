package org.ethereum.util;

import org.ethereum.core.CallTransaction;

import java.util.HashMap;
import java.util.Map;

public class TestContract {
    public final String data;
    public final Map<String, CallTransaction.Function> functions;

    private TestContract(String data, Map<String, CallTransaction.Function> functions) {
        this.data = data;
        this.functions = functions;
    }

    public static TestContract greeter() {
        /*
        contract greeter {

            address owner;
            modifier onlyOwner { if (msg.sender != owner) throw; _ ; }

            function greeter() public {
                owner = msg.sender;
            }
            function greet(string param) onlyOwner constant returns (string) {
                return param;
            }
        }
        */

        String code = "606060405260043610610041576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063ead710c414610046575b600080fd5b341561005157600080fd5b6100a1600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061011c565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100e15780820151818401526020810190506100c6565b50505050905090810190601f16801561010e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610124610187565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561017f57600080fd5b819050919050565b6020604051908101604052806000815250905600a165627a7a723058202aac827affd20fd4d87d358b218eab474248ca8382a5270a7178dfbf15d64ace0029";
        String abi = "[{\"constant\":true,\"inputs\":[{\"name\":\"param\",\"type\":\"string\"}],\"name\":\"greet\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"type\":\"constructor\"}]";

        CallTransaction.Contract contract = new CallTransaction.Contract(abi);

        Map<String, CallTransaction.Function> functions = new HashMap<>();
        functions.put("greet", contract.getByName("greet"));
        return new TestContract(code, functions);
    }

    public static TestContract hello() {
        /*
        contract helloworld {
            function hello() constant returns (string) {
                return "chinchilla";
            }
        }
        */

        String code = "606060405260043610610041576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806319ff1d2114610046575b600080fd5b341561005157600080fd5b6100596100d4565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561009957808201518184015260208101905061007e565b50505050905090810190601f1680156100c65780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100dc610117565b6040805190810160405280600a81526020017f6368696e6368696c6c6100000000000000000000000000000000000000000000815250905090565b6020604051908101604052806000815250905600a165627a7a7230582043fd5aff0f077e6da5e30c15fdba5e724b7e4aa2c190c5ffe86f87f7bf18a47a0029";
        String abi = "[{\"constant\":true,\"inputs\":[],\"name\":\"hello\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"}]";

        CallTransaction.Contract contract = new CallTransaction.Contract(abi);

        Map<String, CallTransaction.Function> functions = new HashMap<>();
            functions.put("hello", contract.getByName("hello"));
        return new TestContract(code, functions);
    }
}
