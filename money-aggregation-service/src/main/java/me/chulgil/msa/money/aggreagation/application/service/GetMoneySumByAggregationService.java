package me.chulgil.msa.money.aggreagation.application.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.chulgil.msa.common.UseCase;
import me.chulgil.msa.money.aggreagation.application.port.in.GetMoneySumByAddressCommand;
import me.chulgil.msa.money.aggreagation.application.port.in.GetMoneySumByAddressUseCase;
import me.chulgil.msa.money.aggreagation.application.port.out.GetMembershipPort;
import me.chulgil.msa.money.aggreagation.application.port.out.GetMoneySumPort;
import me.chulgil.msa.money.aggreagation.application.port.out.MemberMoney;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetMoneySumByAggregationService implements GetMoneySumByAddressUseCase {

    private final GetMoneySumPort getMoneySumPort;
    private final GetMembershipPort getMembershipPort;

    @Override
    public int getMoneySumByAddress(GetMoneySumByAddressCommand command) {

        String targetAddress = command.getAddress();
        List<String> membershipIds = getMembershipPort.getMembershipByAddress(targetAddress);

        List<List<String>> membershipPartitionList = null;
        if (membershipIds.size() > 100) {
            membershipPartitionList = partitionList(membershipIds, 100);
        }

        int sum = 0;
        for (List<String> partitionedList : Objects.requireNonNull(membershipPartitionList)) {
            List<MemberMoney> memberMoneyList = getMoneySumPort.getMoneySumByMembershipIds(partitionedList);

            for (MemberMoney memberMoney : memberMoneyList) {
                sum += memberMoney.getBalance();
            }
        }

        return sum;

    }

    // List를 n개씩 묶어서 List<List<T>>로 만드는 메서드
    private static <T> List<List<T>> partitionList(List<T> list,
                                                   int partitionSize) {
        return IntStream.range(0, list.size())
                        .boxed()
                        .collect(Collectors.groupingBy(index -> index / partitionSize))
                        .values()
                        .stream()
                        .map(indices -> indices.stream()
                                               .map(list::get)
                                               .collect(Collectors.toList()))
                        .collect(Collectors.toList());
    }
}
