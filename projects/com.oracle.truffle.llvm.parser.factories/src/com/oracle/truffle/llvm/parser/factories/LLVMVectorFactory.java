/*
 * Copyright (c) 2016, Oracle and/or its affiliates.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.oracle.truffle.llvm.parser.factories;

import com.oracle.truffle.llvm.nodes.api.LLVMExpressionNode;
import com.oracle.truffle.llvm.nodes.vector.LLVMExtractElementNodeFactory.LLVMDoubleExtractElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMExtractElementNodeFactory.LLVMFloatExtractElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMExtractElementNodeFactory.LLVMI16ExtractElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMExtractElementNodeFactory.LLVMI32ExtractElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMExtractElementNodeFactory.LLVMI64ExtractElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMInsertElementNodeFactory.LLVMDoubleInsertElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMInsertElementNodeFactory.LLVMFloatInsertElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMInsertElementNodeFactory.LLVMI16InsertElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMInsertElementNodeFactory.LLVMI1InsertElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMInsertElementNodeFactory.LLVMI32InsertElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMInsertElementNodeFactory.LLVMI64InsertElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMInsertElementNodeFactory.LLVMI8InsertElementNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMShuffleVectorNodeFactory.LLVMShuffleI32VectorNodeGen;
import com.oracle.truffle.llvm.nodes.vector.LLVMShuffleVectorNodeFactory.LLVMShuffleI8VectorNodeGen;
import com.oracle.truffle.llvm.parser.api.util.LLVMParserRuntime;
import com.oracle.truffle.llvm.runtime.types.LLVMBaseType;

public final class LLVMVectorFactory {

    private LLVMVectorFactory() {
    }

    public static LLVMExpressionNode createInsertElement(LLVMParserRuntime runtime, LLVMBaseType resultType, Object type, LLVMExpressionNode vector, LLVMExpressionNode element,
                    LLVMExpressionNode index) {
        final LLVMExpressionNode target = runtime.allocateVectorResult(type);
        switch (resultType) {
            case I1_VECTOR:
                return LLVMI1InsertElementNodeGen.create(target, vector, element, index);
            case I8_VECTOR:
                return LLVMI8InsertElementNodeGen.create(target, vector, element, index);
            case I16_VECTOR:
                return LLVMI16InsertElementNodeGen.create(target, vector, element, index);
            case I32_VECTOR:
                return LLVMI32InsertElementNodeGen.create(target, vector, element, index);
            case I64_VECTOR:
                return LLVMI64InsertElementNodeGen.create(target, vector, element, index);
            case FLOAT_VECTOR:
                return LLVMFloatInsertElementNodeGen.create(target, vector, element, index);
            case DOUBLE_VECTOR:
                return LLVMDoubleInsertElementNodeGen.create(target, vector, element, index);
            default:
                throw new AssertionError("vector type " + resultType + "  not supported!");
        }
    }

    public static LLVMExpressionNode createExtractElement(LLVMBaseType resultType, LLVMExpressionNode vector, LLVMExpressionNode index) {
        switch (resultType) {
            case I16:
                return LLVMI16ExtractElementNodeGen.create(vector, index);
            case I32:
                return LLVMI32ExtractElementNodeGen.create(vector, index);
            case I64:
                return LLVMI64ExtractElementNodeGen.create(vector, index);
            case FLOAT:
                return LLVMFloatExtractElementNodeGen.create(vector, index);
            case DOUBLE:
                return LLVMDoubleExtractElementNodeGen.create(vector, index);
            default:
                throw new AssertionError(resultType + " not supported!");
        }
    }

    public static LLVMExpressionNode createShuffleVector(LLVMBaseType resultType, LLVMExpressionNode target, LLVMExpressionNode vector1, LLVMExpressionNode vector2, LLVMExpressionNode mask) {
        switch (resultType) {
            case I8_VECTOR:
                return LLVMShuffleI8VectorNodeGen.create(target, vector1, vector2, mask);
            case I32_VECTOR:
                return LLVMShuffleI32VectorNodeGen.create(target, vector1, vector2, mask);
            default:
                throw new AssertionError(resultType);
        }
    }

}
