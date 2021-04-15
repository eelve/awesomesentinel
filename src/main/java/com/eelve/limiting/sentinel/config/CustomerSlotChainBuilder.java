package com.eelve.limiting.sentinel.config;

import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.slotchain.AbstractLinkedProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.DefaultProcessorSlotChain;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotChain;
import com.alibaba.csp.sentinel.slots.DefaultSlotChainBuilder;
import com.alibaba.csp.sentinel.slots.block.authority.AuthoritySlot;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeSlot;
import com.alibaba.csp.sentinel.slots.logger.LogSlot;
import com.alibaba.csp.sentinel.slots.system.SystemSlot;
import com.alibaba.csp.sentinel.util.SpiLoader;

import java.util.List;

/**
 * @ClassName CustomerSlotChainBuilder
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/4/15 23:40
 * @Version 1.0
 **/
public class CustomerSlotChainBuilder extends DefaultSlotChainBuilder {
    @Override
    public ProcessorSlotChain build() {
        ProcessorSlotChain chain =new DefaultProcessorSlotChain();
        List<ProcessorSlot> slotList = SpiLoader.loadPrototypeInstanceListSorted(ProcessorSlot.class);
        for(ProcessorSlot processorSlot : slotList){
            if (processorSlot instanceof DegradeSlot
                || processorSlot instanceof SystemSlot
                || processorSlot instanceof AuthoritySlot
                || processorSlot instanceof LogSlot){
                RecordLog.warn("The ProcessorSlot("+processorSlot.getClass().getCanonicalName()+") will not be add to slotList");
                continue;
            }
            chain.addLast((AbstractLinkedProcessorSlot<?>) processorSlot);
        }
        return chain;
    }
}
