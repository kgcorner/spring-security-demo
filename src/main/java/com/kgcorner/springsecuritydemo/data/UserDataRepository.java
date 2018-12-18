package com.kgcorner.springsecuritydemo.data;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDataRepository extends GenericDataRepository<User> {

    public User getUserByUserName(String userName) {
        List<Operands> operands = new ArrayList<>();
        Operands operand = new Operands(userName, Operands.TYPES.STRING, "user_name", Operands.OPERATORS.EQ);
        operands.add(operand);
        List<User> results = getAll(User.class, operands);
        if(results == null)
            return null;
        if(results.size() == 1)
            return results.get(0);
        else
            throw new IllegalArgumentException("non unique result found, total matched result:"+results.size());
    }
}
