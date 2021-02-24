class Employee {

    private final String id;
    private final String name;
    private final Integer birthYear;
    private final Boolean marriedStatus;

    public Employee(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.birthYear = builder.birthYear;
        this.marriedStatus = builder.marriedStatus;
    }

    static class Builder {
        private String id;
        private String name;
        Integer birthYear;
        Boolean marriedStatus;

        public static Builder newInstance(String id, String name) {
            return new Builder(id, name);
        }

        private Builder(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder setBirthYear(Integer birthYear) {
            this.birthYear = birthYear;
            return this;
        }

        public Builder setMarriedStatus(Boolean marriedStatus) {
            this.marriedStatus = marriedStatus;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthYear=" + (birthYear != null ? birthYear : "null") +
                ", marriedStatus=" + (marriedStatus != null ? marriedStatus : "null") +
                '}';
    }
}

class Test {

    private static volatile Employee employee = Employee.Builder.newInstance("0", "init").build();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                employee = Employee.Builder.newInstance("1", "chloe")
                        .setBirthYear(1996)
                        .build();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                employee = Employee.Builder.newInstance("2", "choi")
                        .setBirthYear(1997)
                        .setMarriedStatus(false)
                        .build();
            }
        });

        t1.start();
        t2.start();

        System.out.println(employee.toString());
        System.out.println(employee.toString());
        System.out.println(employee.toString());
    }
}